package ch.heig.items.api.endpoints;

import ch.heig.items.api.services.SoundsService;
import org.openapitools.api.SoundsApi;
import org.openapitools.model.Sound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SoundsEndPoint implements SoundsApi {
    @Autowired
    private SoundsService soundService;

    @Override
    public ResponseEntity<List<Sound>> getSounds(){
        List<Sound> sounds = soundService.getAll();
        return new ResponseEntity<>(sounds, HttpStatus.OK);
    }
}
