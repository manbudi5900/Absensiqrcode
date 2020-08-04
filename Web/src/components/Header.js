import React, { Component } from 'react';
import {NavLink} from 'react-router-dom';
import { Route} from 'react-router-dom';
import cookie from 'react-cookies';
// import swal from 'sweetalert';
import firebase from 'firebase';

class Header extends Component {
    constructor(props) {
      super(props);
      
      this.state = {
        role : 0
      };
    }
  
    componentWillMount(){
      
    }
    render() {
      return (
        <div>
        <header class="main-header">
            <a href="#" class="logo">
            <span class="logo-mini"><b></b></span>
            <span class="logo-lg"><b>TITIP ABSEN</b></span>
            </a>
            <nav class="navbar navbar-static-top">
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>

            
            </nav>
        </header>
        <aside class="main-sidebar">
            <section class="sidebar">
            
            <div class="user-panel">
                <div class="pull-left image">
                {/* <img src="<?php echo base_url('assets/dist/img/user2-160x160.jpg')?>" class="img-circle" alt="User Image"/> */}
                </div>
                <div class="pull-left info">
                <p></p>
                <div id="status"></div>
                <div id="log"></div>
                </div>
            </div>
            
          
            <ul class="sidebar-menu" data-widget="tree">
                <li class="header">HEADER</li>
                <li class="active"><a href="/"><i class="fa fa-balance-scale"></i> <span>Matakuliah</span></a></li>
                <li class="active"><a href="/absensi"><i class="fa fa-link"></i><span>Password</span></a></li>
                <li class="active"><a href="/"><i class="fa fa-link"></i><span>logout</span></a></li> 
            </ul>
            </section>
            
        </aside>
        </div>
  );
  }
}

export default Header;
