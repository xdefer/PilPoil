'use strict';

angular.module('pilpoilwebApp')
    .controller('BreedController', function ($scope, $state, Breed, BreedSearch) {

        $scope.breeds = [];
        $scope.loadAll = function() {
            Breed.query(function(result) {
               $scope.breeds = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            BreedSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.breeds = result;
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
            $scope.breed = {
                label: null,
                id: null
            };
        };
    });
