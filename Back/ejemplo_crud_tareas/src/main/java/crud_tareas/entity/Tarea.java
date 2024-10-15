package crud_tareas.entity;

import java.time.LocalDateTime;
//import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "tareas")
public class Tarea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column (name="nombreTarea")
	private String nombre;
	private String prioridad;
	private String responsable;
	private String estado;
	@Column (name="fechaRegistro")
	private LocalDateTime createAt;
	@Column (name="fechaCierre")
	private LocalDateTime createAtc;
	private String proyecto;
	
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="tipoTarea_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	//private TipoCliente tipoCliente;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	public LocalDateTime getCreateAtc() {
		return createAtc;
	}
	public void setCreateAtc(LocalDateTime createAtc) {
		this.createAtc = createAtc;
	}
	public String getProyecto() {
		return proyecto;
	}
	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}
	
	
	//public TipoCliente getTipoCliente() {
		//return tipoCliente;
	//}
	//public void setTipoCliente(TipoCliente tipoCliente) {
		//this.tipoCliente = tipoCliente;
	//}
	
	
	

}