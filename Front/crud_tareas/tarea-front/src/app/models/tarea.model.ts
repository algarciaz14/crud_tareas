export class Tarea {
    id?: number;
    nombre!: string;
    prioridad!: string;
    responsable!: string;
    estado!: string;
    proyecto!: string;
    createAt!: Date;   // Fecha de registro
    createAtc!: Date;  // Fecha de cierre

    constructor() {
        this.nombre = '';
        this.prioridad = '';
        this.responsable = '';
        this.estado = '';
        this.proyecto = '';
        this.createAt = new Date();    // Inicializa la fecha de registro a la fecha actual
        this.createAtc = new Date();   // Inicializa la fecha de cierre a la fecha actual 
    }
}
