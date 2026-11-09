package com.example.automotriz.service;

import com.example.automotriz.adapter.ClienteAdapter;
import com.example.automotriz.entity.ClienteEntity;
import com.example.automotriz.model.ClienteModel;
import com.example.automotriz.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final  ClienteAdapter clienteAdapter;

    public ClienteService(ClienteRepository clienteRepository, ClienteAdapter clienteAdapter) {
        this.clienteRepository = clienteRepository;
        this.clienteAdapter = clienteAdapter;
    }

    // devolver lista de todos los clientes
    public List<ClienteModel> listarClientes() {
        return clienteRepository.findByActivoTrue().stream().map(clienteAdapter::toModel).collect(Collectors.toList());
    }

    // devolver lista filtrada de clientes
    public List<ClienteModel> listarClientesFiltrados(String filtro){
        String texto = filtro == null ? "" : filtro.trim().toLowerCase();
        if (texto.isEmpty()) {return listarClientes();}

        return listarClientes().stream()
                .filter(c ->
                        c.getNombre() != null && c.getNombre().toLowerCase().contains(texto) ||
                        c.getDni() != null && c.getDni().toLowerCase().contains(texto) ||
                        c.getCodigo() != null && c.getCodigo().toLowerCase().contains(texto))
                .collect(Collectors.toList());
    }

    //CRU
    @Transactional
    public ClienteModel registrarCliente(ClienteModel model) {
        ClienteEntity entity = clienteAdapter.toEntity(model);

        entity.setIdCliente(null);
        entity.setActivo(true);
        entity.setCodigo("TEMPORAL");

        ClienteEntity clienteGuardado = clienteRepository.saveAndFlush(entity);

        String codigo = String.format("CLI-%03d", clienteGuardado.getIdCliente());
        clienteGuardado.setCodigo(codigo);

        clienteRepository.save(clienteGuardado);
        return clienteAdapter.toModel(clienteGuardado);
    }

    @Transactional
    public void modificarCliente(ClienteModel model){
        ClienteEntity entity = clienteRepository.findById(model.getIdCliente()).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        if (!entity.isActivo()) {throw new IllegalArgumentException("No se puede modificar un cliente desactivado");}

        entity.setNombre(model.getNombre());
        entity.setTelefono(model.getTelefono());
        entity.setCorreo(model.getCorreo());
        entity.setDni(model.getDni());
    }

    public void desactivarCliente(Integer id){
        ClienteEntity entity = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        entity.setActivo(false);
        clienteRepository.save(entity);
    }

    public void activarCliente(Integer id){
        ClienteEntity entity = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        entity.setActivo(true);
        clienteRepository.save(entity);
    }

    public ClienteModel buscarPorId(Integer id) {
        ClienteEntity entity = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        return clienteAdapter.toModel(entity);
    }
}
