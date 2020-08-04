import React, { Component } from 'react';
import {Route} from 'react-router-dom';
import cookie from 'react-cookies';
// import swal from 'sweetalert';
import firebase from 'firebase';
import {DB_CONFIG} from './Config';

import Header from './components/Header';
import Footer from './components/Footer';
import Home from './components/Home';
import Password from './components/Password';
import Absensi from './components/Absensi';

class App extends Component {
  constructor(props) {
    super(props);
    
    this.state = {
      role : 0
    };
  }

  componentWillMount(){
    firebase.initializeApp(DB_CONFIG);
  }

  render() {
      
        
    return (
      <div class="wrapper">
      <Header/>
        <div class="content-wrapper">
        
          <section class="content container-fluid">
          <div>
                <Route path = "/" component = {Home}  exact/>
                <Route path = "/password" component = {Password} exact/>
                <Route path = "/absensi" component = {Absensi}/>
          </div>
          </section>
        </div>
        <Footer/>
      </div>
    );
  }
}
export default App;
