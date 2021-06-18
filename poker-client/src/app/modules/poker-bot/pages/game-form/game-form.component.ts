import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PokerBotService } from '../../poker-bot.service';
import { PokerBotParams } from '../../PokerBotParams';

@Component({
  selector: 'app-game-form',
  templateUrl: './game-form.component.html',
  styleUrls: ['./game-form.component.css'],
})
export class GameFormComponent implements OnInit {
  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private service: PokerBotService
  ) {
    this.form = this.formBuilder.group({
      playingType: ['', Validators.required],
      opponent: ['', Validators.required],
      numOfRounds: ['', Validators.required],
    });
   }

  ngOnInit(): void {
  }

  submit(): void {
    const params = this.getParams();
    this.service.add(params).subscribe();
  }

  get f(): any { return this.form.controls; }

  getParams(): PokerBotParams {
    const params: PokerBotParams ={
      playingType: this.f.playingType.value,
      opponent: this.f.opponent.value,
      numOfRounds: this.f.numOfRounds.value
    }
    return params;
  }

  numberOnly(event: any): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }
}
