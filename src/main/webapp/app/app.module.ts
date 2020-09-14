import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { WalletSharedModule } from 'app/shared/shared.module';
import { WalletCoreModule } from 'app/core/core.module';
import { WalletAppRoutingModule } from './app-routing.module';
import { WalletHomeModule } from './home/home.module';
import { WalletEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { ERC20Module } from 'app/erc20/erc20.module';

@NgModule({
  imports: [
    BrowserModule,
    WalletSharedModule,
    WalletCoreModule,
    WalletHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    WalletEntityModule,
    WalletAppRoutingModule,
    ERC20Module,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class WalletAppModule {}
