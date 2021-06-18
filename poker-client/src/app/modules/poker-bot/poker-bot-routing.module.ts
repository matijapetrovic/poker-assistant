import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GameFormComponent } from './pages/game-form/game-form.component';


const routes: Routes = [
  {
    path: '',
    component: GameFormComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PokerBotRoutingModule { }