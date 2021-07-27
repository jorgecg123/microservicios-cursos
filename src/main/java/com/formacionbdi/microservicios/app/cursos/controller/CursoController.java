package com.formacionbdi.microservicios.app.cursos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicios.app.cursos.services.CursoService;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.CommonController;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursodb = o.get();
		cursodb.setNombre(curso.getNombre());

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursodb));

	}

	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursodb = o.get();

		alumnos.forEach(a -> {
			cursodb.addAlumno(a);
		});

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursodb));
	}

	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursodb = o.get();
		cursodb.removeAlumno(alumno);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursodb));
	}
}
