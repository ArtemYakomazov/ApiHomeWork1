package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for add faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method for find faculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public Faculty editeFaculty(Faculty faculty) {
        logger.info("Was invoked method for edite faculty");
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByName(String name) {
        logger.info("Was invoked method for find faculty by student's name");
        return facultyRepository.findByNameContainsIgnoreCase(name);
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for find faculty by color");
        return facultyRepository.findByColorContainsIgnoreCase(color);
    }

    public String longestNameOfFaculty() {
        return facultyRepository.findAll()
                .stream().map(Faculty::getName)
                .max(Comparator.comparing(String::length)).get();
    }

    public Integer sum() {
        return Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
    }
}