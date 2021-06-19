import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { BotOptions } from './bot-options';
import { PokerBotService } from './poker-bot.service';
import { FormsModule } from '@angular/forms';

export interface Opponent {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'poker-ui';

  opponents: Opponent[] = [
    { value: 'DemoBot/SimpleBot', viewValue: 'Simple Bot' },
    { value: 'DemoBot/AlwaysCallBot', viewValue: 'Always call bot' },
    { value: 'Oursland/ChumpBot', viewValue: 'Chump bot' },
    { value: 'Oursland/FlockBot', viewValue: 'Flock bot' },
    { value: 'PrologBot/PrologBot', viewValue: 'Prolog bot' }
  ];

  form: FormGroup;

  image: any;
  isImageLoading: boolean;

  constructor(private pokerBotService: PokerBotService) {
    this.form = new FormGroup({
      playstyle: new FormControl(0),
      numGames: new FormControl(0),
      opponent: new FormControl([])
    });
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.image = reader.result;
    }, false);
    if (image)
      reader.readAsDataURL(image);
  }

  startGame(): void {
    const botOptions: BotOptions = {
      playstyle: this.form.value.playstyle,
      numGames: this.form.value.numGames,
      opponent: this.form.value.opponent
    };
    this.image = null;
    this.isImageLoading = true;
    this.pokerBotService.startGame(botOptions).subscribe((image) => {
      this.isImageLoading = false;
      this.createImageFromBlob(image);
    }, error => {
      this.isImageLoading = false;
      console.log(error);
    });
  }
}
