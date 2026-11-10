package com.example.automotriz.service;

import com.example.automotriz.adapter.EmpleadoAdapter;
import com.example.automotriz.entity.EmpleadoEntity;
import com.example.automotriz.model.EmpleadoModel;
import com.example.automotriz.repository.EmpleadoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoAdapter empleadoAdapter;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           EmpleadoAdapter empleadoAdapter,
                           PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoAdapter = empleadoAdapter;
        this.passwordEncoder = passwordEncoder;
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
    public void modificarEmpleado(EmpleadoModel model) {
        if (model.getIdEmpleado() == null) {
            throw new IllegalArgumentException("Empleado no encontrado");
        }
        EmpleadoEntity entity = empleadoRepository.findById(model.getIdEmpleado())
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

        if (!entity.isActivo()) {
            throw new IllegalArgumentException("No se puede modificar un empleado desactivado");
        }

        entity.setNombre(model.getNombre());
        entity.setCorreo(model.getCorreo());
        entity.setUsuario(model.getUsuario());
        entity.setRol(model.getRol());

        // Solo cambia la contraseña si escribieron una nueva
        if (model.getContrasenia() != null && !model.getContrasenia().isBlank()) {
            entity.setContrasenia(passwordEncoder.encode(model.getContrasenia()));
        }

        empleadoRepository.saveAndFlush(entity);
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