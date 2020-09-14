import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Erc20Component } from './erc20.component';
import { Erc20RoutingModule } from 'app/erc20/erc20-routing.module';
import { NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [Erc20Component],
  imports: [CommonModule, Erc20RoutingModule, NgbAlertModule, ReactiveFormsModule],
})
export class ERC20Module {}
