import React, { Component } from 'react';
import { Route} from 'react-router-dom';
import cookie from 'react-cookies';
// import swal from 'sweetalert';

class Footer extends Component {
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
        <footer class="main-footer">
                <strong>Copyright &copy; 2019 <a href="#">Titip Absen</a></strong> 
        </footer>

      </div>
    );
  }
}
export default Footer;
