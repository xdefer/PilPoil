'use strict';

describe('Controller Tests', function() {

    describe('AnimalType Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAnimalType, MockAnimal, MockBreed;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAnimalType = jasmine.createSpy('MockAnimalType');
            MockAnimal = jasmine.createSpy('MockAnimal');
            MockBreed = jasmine.createSpy('MockBreed');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'AnimalType': MockAnimalType,
                'Animal': MockAnimal,
                'Breed': MockBreed
            };
            createController = function() {
                $injector.get('$controller')("AnimalTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pilpoilApp:animalTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
