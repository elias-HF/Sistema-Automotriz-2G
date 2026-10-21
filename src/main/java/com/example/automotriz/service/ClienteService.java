package com.example.automotriz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.automotriz.adapter.ClienteAdapter;
import com.example.automotriz.entity.ClienteEntity;
import com.example.automotriz.model.ClienteModel;
import com.example.automotriz.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    private final ClienteAdapter adapter;

    public ClienteService(ClienteRepository repository, ClienteAdapter adapter){
        this.repository = repository;
        this.adapter = adapter;
    }

    public List<ClienteModel> listar(){
        return repository.findAll().stream()
                .map(adapter::toModel)
                .collect(Collectors.toList());
    }

    public List<ClienteModel> listarFiltrado(String filtro){
        String texto = filtro == null ? "" : filtro.trim().toLowerCase();
        if (texto.isEmpty()) {
            return listar();
        }

        return listar().stream()
                .filter(c -> c.getNombre() != null && c.getNombre().toLowerCase().contains(texto)
                        || c.getDni() != null && c.getDni().toLowerCase().contains(texto)
                        || c.getCodigo() != null && c.getCodigo().toLowerCase().contains(texto)
                        || c.getCorreo() != null && c.getCorreo().toLowerCase().contains(texto))
                .collect(Collectors.toList());
    }

    @Transactional
    public ClienteModel registrar(ClienteModel model){
        ClienteEntity entity = adapter.toEntity(model);
        entity.setIdCliente(0);
        entity.setActivo(true);

        ClienteEntity clienteGuardado = repository.save(entity);
        String codigo = String.format("CLI-%03d", clienteGuardado.getIdCliente());
        clienteGuardado.setCod(codigo);
        ClienteEntity guardado = repository.save(clienteGuardado);

        return adapter.toModel(guardado);
    }

    @Transactional
    public void desactivar(int idCliente){
        ClienteEntity entity = repository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        entity.setActivo(false);
        repository.save(entity);
    }

    @Transactional
    public void modificar(ClienteModel model){
        ClienteEntity entity = repository.findById(model.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        entity.setNombre(model.getNombre());
        entity.setTelefono(model.getTelefono());
        entity.setCorreo(model.getCorreo());
        entity.setDni(model.getDni());

        repository.save(entity);
    }

    @Transactional
    public ClienteModel buscarPorId(int id){
        ClienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        return adapter.toModel(entity);
    }
}