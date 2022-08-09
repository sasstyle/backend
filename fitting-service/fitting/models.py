import reserve as reserve

from django.db import models

class Fitting(models.Model):
    userId = models.UUIDField(max_length=100, default='a52403c1-4c7e-4156-9252-56704f486890')
    productId = models.FloatField()

    image = models.ImageField()
    desc = models.CharField(max_length=100, blank=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateField(auto_now=True)

