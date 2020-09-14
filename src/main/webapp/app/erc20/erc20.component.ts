import { Component, OnInit } from '@angular/core';
import { ERC20Service } from 'app/erc20/erc20.service';

@Component({
  selector: 'jhi-erc20',
  templateUrl: './erc20.component.html',
  styleUrls: ['./erc20.component.scss'],
})
export class Erc20Component implements OnInit {
  constructor(public erc20Service: ERC20Service) {
    this.erc20Service.getCoinbaseInfo();
  }

  ngOnInit(): void {}
}
