import React from 'react';
import { Paper, Slider } from 'material-ui';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

class BottomScroll extends React.Component {
  startTimestamp = Date.now() - 86400000;

  constructor(props) {
    super(props);

    this.state = {
      timestamp: Date.now(),
      sliderValue: 1.0,
    };
  }

  componentDidMount() {
    setInterval(() => {
      if (this.state.sliderValue === 1.0) {
        this.setState({ timestamp: Date.now() });
      }
    }, 1000);
  }

  onSliderChange = (e, val) => {
    const diff = Math.round((Date.now() - this.startTimestamp) * this.state.sliderValue);
    this.setState({ sliderValue: val, timestamp: this.startTimestamp + diff });
  };

  prev = 1.0;

  componentDidUpdate() {
    if (this.prev !== this.state.sliderValue || this.state.sliderValue === 1.0) {
      this.prev = this.state.sliderValue;
      this.props.update(this.state.timestamp, null);
    }
  }

  render() {
    const d = new Date(this.state.timestamp);
    const dateStr = `${(`0${d.getHours()}`).slice(-2)}:${(`0${d.getMinutes()}`).slice(-2)}:${(`0${d.getSeconds()}`).slice(-2)} ${(`0${d.getDate()}`).slice(-2)}/${(`0${d.getMonth() + 1}`)}/${`${d.getFullYear()}`}`;

    return (
      <MuiThemeProvider>
        <Paper id="bottomScroll" zDepth={5}>
          <div id="innerScroll">
            <Slider defaultValue={1} id="slider" onChange={this.onSliderChange} step={0.025} />
          </div>
          <div id="textScroll" style={{ fontSize: '200%' }}>
            {dateStr}
          </div>
        </Paper>
      </MuiThemeProvider>
    );
  }
}

export default BottomScroll;
