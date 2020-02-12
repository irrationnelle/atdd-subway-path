/*
 *
 * StationController
 *
 * 0.0.1
 *
 * Copyright 2020 irrationnelle <drakkarverenis@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */
package atdd.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StationController {
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @PostMapping("/stations")
    public ResponseEntity createStation(@RequestBody Station station) {
        Station savedStation = stationRepository.save(station);
        String resultUri = String.format("/stations/%d", savedStation.getId());

        return ResponseEntity.created(URI.create(resultUri))
                             .body(savedStation);
    }

    @GetMapping("/stations")
    public ResponseEntity readStation() {
        List<Station> stations = new ArrayList<Station>();
        stationRepository.findAll()
                         .forEach(stations::add);

        return new ResponseEntity(stations, HttpStatus.OK);
    }

    @GetMapping("/stations/{id}")
    public ResponseEntity readStation(@PathVariable Long id) {
        Optional<Station> optionalStation = stationRepository.findById(id);

        return getResponseEntityForNullableObject(optionalStation);
    }

    @DeleteMapping("/stations/{id}")
    public ResponseEntity deleteStation(@PathVariable Long id) {
        stationRepository.deleteById(id);

        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/lines")
    public ResponseEntity createLine(@RequestBody Line line) {
        Line savedLine = lineRepository.save(line);
        String resultUri = String.format("/lines/%d", savedLine.getId());

        return ResponseEntity.created(URI.create(resultUri))
                             .body(savedLine);
    }

    @GetMapping("/lines")
    public ResponseEntity readLines() {
        List<Line> lines = new ArrayList<Line>();
        lineRepository.findAll()
                      .forEach(lines::add);

        return new ResponseEntity(lines, HttpStatus.OK);
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity readLine(@PathVariable Long id) {
        Optional<Line> optionalLine = lineRepository.findById(id);

        return getResponseEntityForNullableObject(optionalLine);
    }

    @DeleteMapping("/lines/{id}")
    public ResponseEntity deleteLine(@PathVariable Long id) {
        lineRepository.deleteById(id);

        return ResponseEntity.ok()
                             .build();
    }


    private ResponseEntity getResponseEntityForNullableObject(Optional<?> optionalObject) {
        return optionalObject.map(object -> new ResponseEntity(object, HttpStatus.OK))
                             .orElseGet(() -> ResponseEntity.noContent()
                                                            .build());
    }
}
