package com.example.automotriz.service;

import com.example.automotriz.adapter.EmpleadoAdapter;
import com.example.automotriz.entity.EmpleadoEntity;
import com.example.automotriz.model.EmpleadoModel;
import com.example.automotriz.repository.EmpleadoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoAdapter empleadoAdapter;
    private final PasswordEncoder passwordEncoder;
    private final SessionRegistry sessionRegistry;

    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           EmpleadoAdapter empleadoAdapter,
                           PasswordEncoder passwordEncoder,
                           SessionRegistry sessionRegistry) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoAdapter = empleadoAdapter;
        this.passwordEncoder = passwordEncoder;
        this.sessionRegistry = sessionRegistry;
    }

    private void cerrarSesiones(String usuario) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof UserDetails u && u.getUsername().equals(usuario)) {
                sessionRegistry.getAllSessions(principal, false).forEach(SessionInformation::expireNow);
            }
        }
    }

    // Lista todos (activos e inactivos) para mostrar el estado en la tabla
    public List<EmpleadoModel> listarEmpleados() {
        return empleadoRepository.findAll(Sort.by("idEmpleado"))
                .stream().map(empleadoAdapter::toModel).toList();
    }

    public long contarEmpleados() {
        return empleadoRepository.count();
    }

    @Transactional
    public void registrarEmpleado(EmpleadoModel model) {
        EmpleadoEntity entity = empleadoAdapter.toEntity(model);

        entity.setIdEmpleado(null);
        entity.setActivo(true);
        entity.setCodigo("TEMPORAL");
        entity.setContrasenia(passwordEncoder.encode(model.getContrasenia()));

        EmpleadoEntity guardado = empleadoRepository.saveAndFlush(entity);
        guardado.setCodigo(String.format("EMP-%03d", guardado.getIdEmpleado()));
        empleadoRepository.save(guardado);
    }

    @Transactional
    public void modificarEmpleado(EmpleadoModel model, String usuarioActual) {
        if (model.getIdEmpleado() == null) {
            throw new IllegalArgumentException("Empleado no encontrado");
        }
        EmpleadoEntity entity = empleadoRepository.findById(model.getIdEmpleado())
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

        if (!entity.isActivo()) {
            throw new IllegalArgumentException("No se puede modificar un empleado desactivado");
        }

        boolean esPropio = entity.getUsuario().equals(usuarioActual);
        boolean cambiaUsuarioORol = !entity.getUsuario().equals(model.getUsuario())
                || !entity.getRol().equals(model.getRol());
        boolean cambiaClave = model.getContrasenia() != null && !model.getContrasenia().isBlank();

        if (esPropio && cambiaUsuarioORol) {
            throw new IllegalArgumentException("No puedes cambiar tu propio usuario ni tu rol");
        }

        String usuarioAnterior = entity.getUsuario();

        entity.setNombre(model.getNombre());
        entity.setCorreo(model.getCorreo());
        entity.setUsuario(model.getUsuario());
        entity.setRol(model.getRol());
        if (cambiaClave) {
            entity.setContrasenia(passwordEncoder.encode(model.getContrasenia()));
        }

        empleadoRepository.saveAndFlush(entity);

        // Si le cambiaron usuario, rol o clave a otra persona, debe volver a iniciar sesión
        if (!esPropio && (cambiaUsuarioORol || cambiaClave)) {
            cerrarSesiones(usuarioAnterior);
        }
    }

    @Transactional
    public void desactivarEmpleado(Integer id, String usuarioActual) {
        EmpleadoEntity entity = empleadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

        if (entity.getUsuario().equals(usuarioActual)) {
            throw new IllegalArgumentException("No puedes desactivar tu propia cuenta");
        }
        entity.setActivo(false);
        empleadoRepository.save(entity);
        cerrarSesiones(entity.getUsuario());
    }

    @Transactional
    public void activarEmpleado(Integer id) {
        EmpleadoEntity entity = empleadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
        entity.setActivo(true);
        empleadoRepository.save(entity);
    }

    public EmpleadoModel buscarPorId(Integer id) {
        EmpleadoEntity entity = empleadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
        return empleadoAdapter.toModel(entity);
    }
}