angular.module('pilpoilApp')
       .service('SharerService', function() {

        var currentAccount = null;

        var setCurrentAccount = function(CurrentAccount) {
            currentAccount = CurrentAccount;
        };

        var getCurrentAccount = function(){
            return currentAccount;
        };

        return {
          setCurrentAccount: setCurrentAccount,
          getCurrentAccount: getCurrentAccount
        };

      });