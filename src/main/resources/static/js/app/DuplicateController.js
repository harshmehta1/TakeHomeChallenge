(function(){
    angular.module('MyApp')
        .controller("DuplicateController", DuplicateController);

        function DuplicateController($scope, DuplicateService) {

            $scope.duplicateList = {};
            $scope.uniqueList = {};


            DuplicateService.getDuplicates().then(function (value) {
                $scope.duplicateList = value.data;

                DuplicateService.getUnique().then(function (v) {
                    $scope.uniqueList = v.data;
                })
            });

        }
})();