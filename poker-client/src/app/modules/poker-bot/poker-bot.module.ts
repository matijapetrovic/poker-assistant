import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PokerBotRoutingModule } from './poker-bot-routing.module';
import { GameFormComponent } from '../poker-bot/pages/game-form/game-form.component';
import { CoreModule } from 'src/app/core/core.module';

import { VendorsModule } from 'src/app/vendors/lib/vendors.module';

@NgModule({
  declarations: [GameFormComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PokerBotRoutingModule,
    CoreModule,
    VendorsModule,
  ]
})
export class PokerBotModule { }