'use strict';

angular.module('pilpoilwebApp')
    .controller('AdTypeController', function ($scope, $state, AdType, AdTypeSearch) {

        $scope.adTypes = [];
        $scope.loadAll = function() {
            AdType.query(function(result) {
               $scope.adTypes = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AdTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.adTypes = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.adType = {
                label: null,
                id: null
            };
        };
    });
