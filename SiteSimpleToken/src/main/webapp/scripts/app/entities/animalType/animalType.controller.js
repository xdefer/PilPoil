'use strict';

angular.module('pilpoilwebApp')
    .controller('AnimalTypeController', function ($scope, $state, AnimalType, AnimalTypeSearch) {

        $scope.animalTypes = [];
        $scope.loadAll = function() {
            AnimalType.query(function(result) {
               $scope.animalTypes = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            AnimalTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.animalTypes = result;
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
            $scope.animalType = {
                label: null,
                id: null
            };
        };
    });
