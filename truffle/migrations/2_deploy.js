const SimpleERC20 = artifacts.require('SimpleERC20');

require('@openzeppelin/test-helpers/configure')({ provider: web3.currentProvider, environment: 'truffle' });

module.exports = function (deployer) {
  deployer.deploy(SimpleERC20, "DEMO", "simple20").then(async (i) => {
    console.log("Complete deployment, SimpleERC20's contract address: " + i.address)
  });
};
