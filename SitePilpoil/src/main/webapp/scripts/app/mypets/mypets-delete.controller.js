'use strict';

angular.module('pilpoilApp').controller('MypetsDeleteController', function ($scope, $uibModalInstance, entity, Animal) {
	
	$scope.animal = entity;
	
    $scope.clear = function() {
        $uibModalInstance.dismiss('cancel');
    };
    
    $scope.confirmDelete = function (id) {
        Animal.delete({id: id},
            function () {
                $uibModalInstance.close(true);
            });
    };
	
});
