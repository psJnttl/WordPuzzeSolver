import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

class Words extends React.Component {
  constructor(props) {
    super(props);
    this.state = {wordCount:0, itemsPerPage:10,}
  }

  getWordCount() {
    const self = this;
    const config = {headers: {'X-Requested-With': 'XMLHttpRequest'}};
    axios.get('/api/words/count', config)
         .then(function (response) {
           const resp = response.data;
           self.setState({wordCount: resp.count});
         })
         .catch(function (error) {
           console.log("fetching count failed");
         });

  }

  componentWillMount() {
    this.getWordCount();
  }

  render() {
    return (
      <div>Words in database: {this.state.wordCount}

      </div>
    );
  }
}
Words.PropTypes = {}
Words.defaultProps = {}
export default Words;
