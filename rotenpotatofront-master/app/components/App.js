import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'jquery';
import BottomScroll from './BottomScroll';
import Search from './Search';

/* global google */

class App extends React.Component {
  map = null;
  heatMap = null;
  host = 'http://172.30.2.36:8080/inventory?';
  // url = 'http://172.30.2.36:8080/inventory?category=lemon&timestamp=1479249798772';

  constructor(props) {
    super(props);

    this.state = {
      defaults: {
        lat: 47.3902,
        lng: 8.5150,
      },
      timestamp: Date.now(),
      search: '',
    };
  }

  componentDidMount() {
    setInterval(() => {
      this.setState({
        timestamp: Date.now(),
      });
    }, 1000);

    this.map = new google.maps.Map(this.refs.map, {
      center: { lat: this.state.defaults.lat, lng: this.state.defaults.lng },
      zoom: 16,
      disableDefaultUI: true,
      styles: [
        { featureType: 'poi', stylers: [{ visibility: 'off' }] },
        { featureType: 'transit', stylers: [{ visibility: 'off' }] },
      ],
    });

    this.addPanel(this.map, <BottomScroll timestamp={this.state.timestamp} update={this.updateHeatmap} />, google.maps.ControlPosition.TOP_RIGHT);
    this.addPanel(this.map, <Search update={this.updateHeatmap} />, google.maps.ControlPosition.TOP_LEFT);
  }

  prevTs = null;
  prevCat = null;

  updateHeatmap = (ts, cat) => {
    if (ts !== null) {
      this.prevTs = ts;
    }
    if (cat !== null) {
      if (cat === '') {
        this.prevCat = null;
      }
      this.prevCat = cat;
    }

    if (this.prevCat !== null && this.prevTs !== null) {
      const url = `${`${this.host}category=${this.prevCat}` + '&timestamp='}${this.prevTs}`;
      console.log(url);
      $.getJSON(url, (data) => {
        console.log(data);
        if (this.heatMap !== null) {
          this.heatMap.setData(this.transform(data));
        } else {
          this.heatMap = new google.maps.visualization.HeatmapLayer({
            data: this.transform(data),
            dissipating: false,
            map: this.map,
          });
        }
      });
    }
  };

  transform = (data) => {
    const out = data.geoInventories.map((s) => {
      const times = s.items.map(x => x.quantity).reduce((a, b) => a + b, 0);
      const out = [];
      for (let i = 0; i < Math.round(times); i++) {
        out.push(new google.maps.LatLng(s.lat, s.lng));
      }
      return out;
    });

    return out.reduce((a, b) => a.concat(b), []);
  };

  addPanel = (map, panel, position) => {
    const container = document.createElement('div');
    ReactDOM.render(panel, container);
    this.map.controls[position].push(container);
  };

  render() {
    return (
      <div ref="map" id="map">Loading map...</div>
    );
  }
}

export default App;
