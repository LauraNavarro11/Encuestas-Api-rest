package com.api.rest.apirestencuestas.controller.v2;

import com.api.rest.apirestencuestas.model.Voto;
import com.api.rest.apirestencuestas.model.repositories.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController("EncuestaControllerV2")
@RequestMapping("/v2")
public class VotoRestController {
    @Autowired
    private VotoRepository votoRepository;
    @PostMapping("/encuestas/{encuestaId}/votos")
    public ResponseEntity<?> crearVoto(@PathVariable Long encuestaId, @RequestBody Voto voto){
        voto = votoRepository.save(voto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(voto.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }
    @GetMapping("/encuestas/{encuestaId}/votos")
    public Iterable<Voto> listarTodosLosVotos(@PathVariable Long encuestaId){
        return votoRepository.findByEncuesta(encuestaId);
    }
}
