package crud_tareas.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crud_tareas.dto.TareaDto;
import crud_tareas.entity.Tarea;
import crud_tareas.repository.ITareaRepository;
//import jakarta.transaction.Transactional;

@Service
public class TareaService {
	
	@Autowired
	private ITareaRepository tareaRepository;
	
	//Consulta de todas las tareas
	@Transactional(readOnly = true)
	public List<Tarea> findAll(){
		return (List<Tarea>)tareaRepository.findAll();
		
	}
	
	//Consulta por ID
	@Transactional(readOnly = true)
	public Tarea findById(Long id) {
		return (Tarea) tareaRepository.findById(id).orElse(null);
	}
	
	//Crear nueva tarea
	@Transactional
	public Tarea createTarea (TareaDto tarea) {
		Tarea tareaEntity= new Tarea();
		tareaEntity.setNombre(tarea.getNombre());
		tareaEntity.setPrioridad(tarea.getPrioridad());
		tareaEntity.setResponsable(tarea.getResponsable());
		tareaEntity.setEstado(tarea.getEstado());
		tareaEntity.setProyecto(tarea.getProyecto());
		tareaEntity.setCreateAt(tarea.getCreateAt());
		tareaEntity.setCreateAtc(tarea.getCreateAtc());


	    // Establecer la fecha de creación a la fecha actual
	    tareaEntity.setCreateAt(LocalDateTime.now());

	    // Solo establecemos la fecha de cierre si está disponible
	    if (tarea.getCreateAtc() != null) {
	        tareaEntity.setCreateAtc(tarea.getCreateAtc());
	    } else {
	        
	        tareaEntity.setCreateAtc(null); 
	    }

	    return tareaRepository.save(tareaEntity);
	}

	
	//Eliminar tarea
	@Transactional
	public void delete (Long id) {
		tareaRepository.deleteById(id);
	}
	
	
	@Transactional
	public Tarea updateTarea(Tarea tarea) {
	    // Guardar la tarea actualizada en la base de datos
	    return tareaRepository.save(tarea); //
	}


	
}