from fitting.utils import delete_fitting, virtual_try_on
from fitting.models import Fitting
from fitting.serializers import FittingListSerializer, FittingPostSerializer, FittingDetailSerializer, FittingUpdateSerializer, FittingPostParamsSerializer

from django.core.files import File  
from django.http import Http404

from rest_framework.views import APIView
from rest_framework import status
from rest_framework.response import Response
from rest_framework.parsers import MultiPartParser, JSONParser

from drf_yasg.utils       import swagger_auto_schema

class FittingListView(APIView):
    parser_classes = [MultiPartParser, JSONParser, ]

    @swagger_auto_schema(operation_description='피팅 리스트 조회', request_body=None, responses={"200": FittingListSerializer(many=True)}) 
    def get(self, request):
        fittings = Fitting.objects.all()
        serializer = FittingListSerializer(fittings, many = True)
        return Response(serializer.data)

    @swagger_auto_schema(operation_description='피팅 등록', request_body=FittingPostParamsSerializer, responses={"200": FittingPostSerializer}) 
    def post(self, request):

        profileUrl = request.data.get("profileUrl")
        image = request.data.get("clothUrl")
        
        profile_name = 'profile.png'
        cloth_name = 'cloth.png'

        result = virtual_try_on(profileUrl, image, profile_name, cloth_name)

        with open(result, 'rb') as img:
            data = {
                'userId': request.data.get("userId"),
                'productId': request.data.get("productId"), 
                'image': File(img), 
                'desc': request.data.get("desc"), 
            }
        
            serializer = FittingPostSerializer(data=data)
            if serializer.is_valid():
                serializer.save()
                
                img.close()
                delete_fitting(request)
                return Response(serializer.data, status=status.HTTP_201_CREATED)
        
        delete_fitting(request)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class FittingAPIView(APIView):
    parser_classes = [JSONParser, ]

    def get_object(self, pk):
        try:
            return Fitting.objects.get(pk=pk)
        except Fitting.DoesNotExist:
            raise Http404
    
    @swagger_auto_schema(operation_description='피팅 상세 조회', request_body=None, responses={"200": FittingDetailSerializer}) 
    def get(self, request, pk, format=None):
        fitting = self.get_object(pk)
        serializer = FittingDetailSerializer(fitting)
        return Response(serializer.data)

    @swagger_auto_schema(operation_description='피팅 수정', request_body=FittingUpdateSerializer, responses={"200": FittingUpdateSerializer}) 
    def put(self, request, pk, format=None):
        fitting = self.get_object(pk)
        serializer = FittingUpdateSerializer(fitting, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @swagger_auto_schema(operation_description='피팅 삭제', request_body=None, responses={"204": "No Content"}) 
    def delete(self, request, pk, format=None):
        fitting = self.get_object(pk)
        fitting.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)




