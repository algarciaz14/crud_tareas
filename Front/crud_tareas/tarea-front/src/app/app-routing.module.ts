import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TareasComponent} from './components/tareas/tareas.component';

const routes: Routes = [
  { path: 'tareas', component: TareasComponent },
  { path: '', redirectTo: '/tareas', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

