import { TestBed } from '@angular/core/testing';

import { PokerBotService } from './poker-bot.service';

describe('PokerBotService', () => {
  let service: PokerBotService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PokerBotService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
