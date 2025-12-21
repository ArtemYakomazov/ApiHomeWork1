package ru.hogwarts.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Faculty {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    private Collection<Student> students;

    public Faculty(long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id && Objects.equals(color, faculty.color) && Objects.equals(name, faculty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Collection<Student> getStudents() {
        return students;
    }
}
