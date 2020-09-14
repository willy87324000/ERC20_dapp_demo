import { Erc20Component } from 'app/erc20/erc20.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Routes = [
  {
    path: '',
    component: Erc20Component,
    canActivate: [UserRouteAccessService],
    data: {
      authorities: [],
      pageTitle: 'ERC20 Dapp',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class Erc20RoutingModule {}
