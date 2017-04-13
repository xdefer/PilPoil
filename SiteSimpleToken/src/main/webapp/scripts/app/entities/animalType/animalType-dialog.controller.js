'use strict';

angular.module('pilpoilwebApp').controller('AnimalTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'AnimalType', 'Animal', 'Breed',
        function($scope, $stateParams, $uibModalInstance, entity, AnimalType, Animal, Breed) {

        $scope.animalType = entity;
        $scope.animals = Animal.query();
        $scope.breeds = Breed.query();
        $scope.load = function(id) {
            AnimalType.get({id : id}, function(result) {
                $scope.animalType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pilpoilwebApp:animalTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.animalType.id != null) {
                AnimalType.update($scope.animalType, onSaveSuccess, onSaveError);
            } else {
                AnimalType.save($scope.animalType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
