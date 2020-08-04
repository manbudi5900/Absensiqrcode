import QRCode from 'qrcode.react';
import React, { Component } from 'react';
import { Route} from 'react-router-dom';
import cookie from 'react-cookies';
import swal from 'sweetalert';
import firebase from 'firebase';
import {NavLink} from 'react-router-dom';

class Home extends Component {
  constructor(props) {
    super(props);
    
    this.state = {
        data    :[],
        cari : '',
    };
    this.todosData = firebase.database().ref().child("matakuliah");
    this.handleCari = this.handleCari.bind(this);
    this.handleQR = this.handleQR.bind(this);
  }
  handleCari(event) {
    this.setState({cari : event.target.value})
  }
  handleQR(kode,kode1,namaM,nama,Dosen){
    var tanggal = new Date();
    
    swal({
        title: "Pilih OK untuk lanjut Pengguna ini",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      })
      .then((willDelete) => {
        if (willDelete) {
            // var React = require('react');
            // var QRCode = require('qrcode.react');
             
            // React.render(
            //   <QRCode value="http://facebook.github.io/react/" />,
            //   mountNode
            // );
        
        } else {}
      });
}




  componentWillMount(){
    this.todosData.on('value', snap => {
        var datanya = [];
        snap.forEach(element => {
            var dataq; 
                dataq           = element.val();
                dataq['.key']   = element.key;
                datanya.push(dataq);
        });
        this.setState({
            data : datanya
           })
           
    });

  }

  render() {
    const filteredCountries = this.state.data.filter( KodeM =>{
        return KodeM.KodeM.toLowerCase().indexOf( this.state.cari.toLowerCase() ) !== -1})
    return (
      <div>
        <div class="container">
	<div class="panel panel-default">
		<div class="panel-body">
			<h5><i class="fa fa-cube fa-fw"></i> Home <i class="fa fa-angle-right fa-fw"></i></h5>

			

            <div class="table-responsive">
            
				<div data-toggle="modal" data-target="#exampleModal" id="my-grid_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="col-sm-5">
						<div id="my-grid_filter" class="dataTables_filter">
							<label>
								<i class="fa fa-search fa-fw"></i> 
								Pencarian : <input id="search_text" type="search" class="form-control input-sm" onChange={this.handleCari} placeholder="" aria-controls="my-grid"/>
							</label>
						</div>
					</div>
					
				</div>
				<div id="result">
                <table  class="table table-striped  dataTable no-footer" role="grid" aria-describedby="my-grid_info" style={{width: '95%'}}>
            <thead>
                <tr role="row">
                    <th class="no-sort sorting_disabled" rowspan="1" colspan="1" aria-label="#: activate to sort column descending" style={{width: '79px'}} aria-sort="ascending">#</th>
                    <th class="no-sort sorting_disabled" rowspan="1" colspan="1" aria-label="Merek: activate to sort column ascending" style={{width: '282px'}}>Kode Dosen</th>
                    <th class="no-sort sorting_disabled" rowspan="1" colspan="1" aria-label="Merek: activate to sort column ascending" style={{width: '282px'}}>Nama Dosen</th>
                    <th class="no-sort sorting_disabled" rowspan="1" colspan="1" aria-label="Merek: activate to sort column ascending" style={{width: '282px'}}>Kode Matakuliah</th>
                    <th class="no-sort sorting_disabled" rowspan="1" colspan="1" aria-label="Merek: activate to sort column ascending" style={{width: '282px'}}>Nama Matakuliah</th>
                    <th class="no-sort sorting_disabled" rowspan="1" colspan="1" aria-label="Edit" style={{width: '207px'}}>Absensi</th>
                    
                </tr>
            </thead>
            <tbody>
            {
                filteredCountries.map((adm) => {
                    return( 
                <tr role="row" class="odd" key={adm['.key'] }>
                    <td class="sorting_1">1</td>
                    <td>{adm['KodeD']}</td>
                    <td>{adm['NamaD']}</td>
                    <td>{adm['KodeM']}</td>
                    <td>{adm['NamaM']}</td>
                    <td>
                  
                      <button><a href = {'absensi/' + adm['KodeD'] + '/'+ adm['KodeM']}>Absen</a></button> 
            
                    {/* <QRCode
                        id="123456"
                        value={adm['KodeM']+'/'+adm['NamaM']+'/'+adm['DosenP']+'/'+new Date()}
                        size={290}
                        level={"H"}
                        includeMargin={true}
                    /> */}
                    </td>
                </tr>
                    )
                })
            }          

            </tbody>
    </table>
    <div id="qrcode"></div>

                </div>
							
			</div>
			</div>
		</div>
		
	</div>

      </div>
    );
  }
}
export default Home;



