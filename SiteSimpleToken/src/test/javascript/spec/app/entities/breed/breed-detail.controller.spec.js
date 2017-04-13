'use strict';

describe('Controller Tests', function() {

    describe('Breed Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBreed, MockAnimal, MockAnimalType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBreed = jasmine.createSpy('MockBreed');
            MockAnimal = jasmine.createSpy('MockAnimal');
            MockAnimalType = jasmine.createSpy('MockAnimalType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Breed': MockBreed,
                'Animal': MockAnimal,
                'AnimalType': MockAnimalType
            };
            createController = function() {
                $injector.get('$controller')("BreedDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pilpoilwebApp:breedUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
