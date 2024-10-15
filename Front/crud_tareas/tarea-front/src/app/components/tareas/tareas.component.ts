import { Component, OnInit } from '@angular/core';
import { TareaService } from '../../services/tarea.service';
import { Tarea } from '../../models/tarea.model';



@Component({
  selector: 'app-tareas',
  templateUrl: './tareas.component.html',
  styleUrls: ['./tareas.component.css']
})
export class TareasComponent implements OnInit {
  tareas: Tarea[] = [];
  nuevaTarea: Tarea;

  constructor(private  tareaService: TareaService) {
    this.nuevaTarea = new Tarea(); // Inicializamos la nueva tarea
  }

  ngOnInit(): void {
    this.obtenerTareas(); // Obtenemos la lista de tareas al cargar
  }

  obtenerTareas(): void {
    this.tareaService.getTareas().subscribe(tareas => {
      this.tareas = tareas;
    });
  }


  agregarTarea(): void {
    if (this.nuevaTarea.id) {
      // Si la tarea tiene un ID, actualizamos la tarea
      this.tareaService.updateTarea(this.nuevaTarea).subscribe(() => {
        this.obtenerTareas(); // Reconsultamos las tareas después de la actualización
        this.nuevaTarea = new Tarea(); // Limpiamos el formulario después de la actualización
      }, error => {
        console.error('Error al actualizar la tarea:', error);
      });
    } else {
      // Si no tiene ID, creamos una nueva tarea
      this.tareaService.createTarea(this.nuevaTarea).subscribe(() => {
        this.obtenerTareas(); // Reconsultamos las tareas después de agregar una nueva
        this.nuevaTarea = new Tarea(); // Limpiamos el formulario después de agregar
      }, error => {
        console.error('Error al agregar la tarea:', error);
      });
    }
  }
  
  

  eliminarTarea(id: number): void {
    this.tareaService.deleteTarea(id).subscribe(() => {
      this.tareas = this.tareas.filter(tarea => tarea.id !== id); // Elimina la tarea de la lista
      console.log(`Tarea con ID ${id} eliminada.`); // Log para verificar
    }, error => {
      console.error('Error al eliminar la tarea:', error); // Manejo de error
    });
  }

  updateTarea(tarea: Tarea): void {
    this.nuevaTarea = { ...tarea }; // Copiamos la tarea seleccionada al modelo del formulario
  }
  
}