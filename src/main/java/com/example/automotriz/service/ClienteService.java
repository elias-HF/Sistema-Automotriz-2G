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
    private final ClienteRepository repository;
    private final ClienteAdapter adapter;

    public ClienteService(ClienteRepository repository, ClienteAdapter adapter){
        this.repository = repository;
        this.adapter = adapter;
    }

    public List<ClienteModel> listar(){
        List<ClienteEntity> entities = repository.findAll();
        return entities.stream().map(e -> adapter.toModel(e)).collect(Collectors.toList());
    }

    @Transactional
    public ClienteModel registrar(ClienteModel model){
        ClienteEntity entity = adapter.toEntity(model);
        entity.setIdCliente(0);
        entity.setActivo(true);

        ClienteEntity clienteGuardado = clienteRepository.save(cliente);
        String codigo = String.format("CLI-%03d", clienteGuardado.getIdCliente());

        clienteGuardado.setCod(codigo);

        ClienteEntity guardado = repository.save(entity);

        return adapter.toModel(guardado);
    }

    @Transactional
    public void desactivar(ClienteModel model){
        ClienteEntity entity = repository.findById(model.getIdCliente()).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        entity.setActivo(false);
    }

    @Transactional
    public void modificar(ClienteModel model){
        ClienteEntity entity = repository.findById(model.getIdCliente()).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        entity.setNombre(model.getNombre());
        entity.setTelefono(model.getTelefono());
        entity.setCorreo(model.getCorreo());
        entity.setDni(model.getDni());

        repository.save(entity);
    }

    public List<ClienteModel> listarFiltrado(String filtro){
        String texto = filtro == null ? "" : filtro.trim().toLowerCase();
        return listar().stream().filter(
                c -> c.getNombre().toLowerCase().contains(texto) || c.getDni().contains(texto) || c.getCodigo().toLowerCase().contains(texto) || c.getCorreo().toLowerCase().contains(texto))
                .collect(Collectors.toList());
    }
}