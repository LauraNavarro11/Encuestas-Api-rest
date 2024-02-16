package com.api.rest.apirestencuestas.controller.v2;

import com.api.rest.apirestencuestas.dto.OpcionCount;
import com.api.rest.apirestencuestas.dto.VotoResult;
import com.api.rest.apirestencuestas.model.Voto;
import com.api.rest.apirestencuestas.model.repositories.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("ComputeControllerV2")
@RequestMapping("/v2")
public class ComputeResultController {
    @Autowired
    private VotoRepository votoRepository;
    @GetMapping("/calcularResultado")
    public ResponseEntity<?> calcularResultado(@RequestParam Long encuestaId){
        VotoResult votoResult = new VotoResult();
        Iterable<Voto> votos = votoRepository.findByEncuesta(encuestaId);

        //Algoritmo para contar votos
        int totalVotos=0;
        Map<Long, OpcionCount> tempMap = new HashMap<Long, OpcionCount>();

        for(Voto v:votos){
            totalVotos++;
            //Obtenemos la OpcionCount correspondiente a esta opcion
            OpcionCount opcionCount = tempMap.get(v.getOpcion().getId());
            if(opcionCount == null){
                opcionCount= new OpcionCount();
                opcionCount.setOpcionId(v.getOpcion().getId());
                tempMap.put(v.getOpcion().getId(),opcionCount);
            }
            opcionCount.setCount(opcionCount.getCount()+1);
        }
        votoResult.setTotalVotos(totalVotos);
        votoResult.setResults(tempMap.values());
        return new ResponseEntity<>(votoResult, HttpStatus.OK);

    }
}
