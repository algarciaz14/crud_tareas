package crud_tareas.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import crud_tareas.dto.TareaDto;
import crud_tareas.entity.Tarea;
import crud_tareas.service.TareaService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class TareaRestController {
	
	@Autowired
	private TareaService tareaService;
	
	
	@PostMapping
    public ResponseEntity<Tarea> createTarea(@RequestBody TareaDto tareaDto) {
        Tarea nuevaTarea = tareaService.createTarea(tareaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarea);
    }
	
	//Leer todas las tareas
	@GetMapping("/tareas")
	@ResponseStatus(HttpStatus.OK)
	public List<Tarea> consulta(){
		return tareaService.findAll();
	}
	
	
	
	//Obtener una tarea con id especifico 
	
	@GetMapping("/tareas/{id}")
	public ResponseEntity<?> consultaPorID(@PathVariable Long id){
		Tarea tarea = null;
		String response="";
		try {
			tarea = tareaService.findById(id);
		} catch(DataAccessException e) {
			response= "Error al realizar la consulta.";
			response= response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(tarea == null)
		{
			response= "La tarea con el ID:".concat(id.toString()).concat("no existe en la base de datos");
			return new ResponseEntity<String>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Tarea>(tarea,HttpStatus.OK);
	}
	
	
	//Eliminar la tarea del id especificado
	@DeleteMapping("/tareas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Tarea tareaDelete = this.tareaService.findById(id);
			if(tareaDelete== null)
			{
				response.put("mensaje", "Error al eliminar. La tarea no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			tareaService.delete(id);
		} catch (DataAccessException e) { 
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Tarea eliminada con éxito");
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/tareas")
	public ResponseEntity<?> create(@RequestBody TareaDto tarea) {
	    Tarea tareaNew = null;
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // Si el DTO no tiene una fecha de cierre, la asignamos aquí
	        if (tarea.getCreateAtc() == null) {
	            tarea.setCreateAtc(LocalDateTime.now().plusDays(7)); // Por ejemplo, 7 días después de la creación
	        }

	        tareaNew = this.tareaService.createTarea(tarea);
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar el insert");
	        response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    response.put("mensaje", "Tarea agregada con éxito, con el ID " + tareaNew.getId());
	    response.put("tarea", tareaNew);
	    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	
	//Actualizar Tarea
	@PutMapping("/tareas/{id}")
	public ResponseEntity<?> update(@RequestBody TareaDto tareaDto, @PathVariable Long id) {
	    Map<String, Object> response = new HashMap<>();
	    
	    try {
	        // Verificamos si la tarea existe en la base de datos
	        Tarea tareaActual = tareaService.findById(id);
	        if (tareaActual == null) {
	            response.put("mensaje", "Error: no se pudo editar, el cliente ID: ".concat(id.toString()).concat(" no existe en la base de datos."));
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	        }

	        // Actualizamos solo los campos no nulos del cliente actual
	        if (tareaDto.getNombre() != null) {
	            tareaActual.setNombre(tareaDto.getNombre());
	        }
	        if (tareaDto.getPrioridad() != null) {
	            tareaActual.setPrioridad(tareaDto.getPrioridad());
	        }
	        if (tareaDto.getResponsable() != null) {
	            tareaActual.setResponsable(tareaDto.getResponsable());
	        }
	        if (tareaDto.getEstado() != null) {
	            tareaActual.setEstado(tareaDto.getEstado());
	        }
	        if (tareaDto.getProyecto() != null) {
	            tareaActual.setProyecto(tareaDto.getProyecto());
	        }
	        
	     

	        // Guardamos la tarea actualizada en la base de datos
	        Tarea tareaUpdated = tareaService.updateTarea(tareaActual); // Se llama al método que guarda la tarea actualizado

	        response.put("mensaje", "El cliente ha sido actualizado con éxito");
	        response.put("tarea", tareaUpdated); // Regresamos la tarea actualizada
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	        
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al actualizar la tarea en la base de datos");
	        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
