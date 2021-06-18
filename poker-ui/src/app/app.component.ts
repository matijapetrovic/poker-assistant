import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { BotOptions } from './bot-options';
import { PokerBotService } from './poker-bot.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'poker-ui';

  form: FormGroup;

  results: string;

  constructor(private pokerBotService: PokerBotService) {
    this.form = new FormGroup({
      playstyle: new FormControl(0),
      numGames: new FormControl(0),
      opponents: new FormControl([])
    });
  }

  startGame(): void {
    const botOptions: BotOptions = {
      playstyle: this.form.value.playstyle,
      numGames: this.form.value.numGames,
      opponents: this.form.value.opponents
    };
    this.pokerBotService.startGame(botOptions).subscribe((results) => this.results = results);
  }
}
