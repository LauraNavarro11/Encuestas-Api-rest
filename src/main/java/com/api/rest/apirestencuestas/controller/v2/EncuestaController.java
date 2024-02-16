package com.api.rest.apirestencuestas.controller.v2;

import com.api.rest.apirestencuestas.exception.ResourceNotFoundException;
import com.api.rest.apirestencuestas.model.Encuesta;
import com.api.rest.apirestencuestas.model.repositories.EncuestaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController("VotoControllerV2")
@RequestMapping("/v2")
public class EncuestaController {
    @Autowired
    private EncuestaRepository encuestaRepository;

    @GetMapping("/encuestas")
    public ResponseEntity<Iterable<Encuesta>> listarTodasLasEncuestas(Pageable pageable){
        Page <Encuesta> encuesta= encuestaRepository.findAll(pageable);
        return new ResponseEntity<>(encuesta, HttpStatus.OK);
    }
    @PostMapping("/encuestas")
    public ResponseEntity<?> crearEncuestas(@Valid @RequestBody Encuesta encuesta){
        encuesta= encuestaRepository.save(encuesta);
        HttpHeaders httpHeaders= new HttpHeaders();
        URI newEncuestaUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(encuesta.getId()).toUri();
        httpHeaders.setLocation(newEncuestaUri);
        return new ResponseEntity<>(null,httpHeaders,HttpStatus.CREATED);

    }
    @GetMapping("/encuestas/{encuestaId}")
        public ResponseEntity<?> obtenerEncuestas(@PathVariable Long encuestaId){
        verifyEncuesta(encuestaId);
        Optional<Encuesta> encuesta = encuestaRepository.findById(encuestaId);
        if(encuesta.isPresent()){
            return new ResponseEntity<>(encuesta,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/encuestas/{encuestaId}")
    public ResponseEntity<?> actualizarEncuestas(@Valid @RequestBody Encuesta encuesta,@PathVariable Long encuestaId){
        encuesta.setId(encuestaId);
        encuestaRepository.save(encuesta);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> eliminarEncuesta(@PathVariable Long encuestaId){
        encuestaRepository.deleteById(encuestaId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    protected void verifyEncuesta(Long encuestaId){
        Optional<Encuesta> encuesta = encuestaRepository.findById(encuestaId);
        if(!encuesta.isPresent()){
            throw new ResourceNotFoundException("Encuesta con el Id "+encuestaId+" no se encuentra disponible");
        }
    }

}
