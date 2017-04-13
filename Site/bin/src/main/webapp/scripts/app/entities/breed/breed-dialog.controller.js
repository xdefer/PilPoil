'use strict';

angular.module('pilPoilApp').controller('BreedDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Breed', 'Animal', 'AnimalType',
        function($scope, $stateParams, $uibModalInstance, entity, Breed, Animal, AnimalType) {

        $scope.breed = entity;
        $scope.animals = Animal.query();
        $scope.animaltypes = AnimalType.query();
        $scope.load = function(id) {
            Breed.get({id : id}, function(result) {
                $scope.breed = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pilPoilApp:breedUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.breed.id != null) {
                Breed.update($scope.breed, onSaveSuccess, onSaveError);
            } else {
                Breed.save($scope.breed, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
