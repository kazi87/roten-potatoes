import React from 'react';
import { Paper, AutoComplete } from 'material-ui';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

class Search extends React.Component {
  labels1 = ['potatoes', 'tomatoes', 'lemons', 'butter', 'onions', 'bananas', 'milk', 'cola', 'chicken', 'feta'];
  labels2 = ['vegatable', 'healthy food', 'cool', 'red', 'fruit', 'yellow', 'oil', 'fat', 'unhealthy food', 'musli', 'drink', 'white', 'cow', 'butter', 'dark', 'pepsi', 'green', 'leaf', 'animal', 'breast', 'grill', 'olives'];

  onRequest = (str) => {
    this.props.update(null, str);
  };

  render() {
    return (
      <MuiThemeProvider>
        <Paper id="search" zDepth={5}>
          <div id="searchInside">
            <AutoComplete
              hintText={'Search for product...'}
              filter={AutoComplete.fuzzyFilter}
              dataSource={this.labels1.concat(this.labels2)}
              maxSearchResults={5}
              onNewRequest={this.onRequest}
              fullWidth
              openOnFocus
            />
          </div>
        </Paper>
      </MuiThemeProvider>
    );
  }
}

export default Search;
