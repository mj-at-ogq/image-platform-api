package me.ogq.ocp.sample.usecase.image.exception

import java.lang.IllegalArgumentException

class NotExistImageException(imageId: Long): IllegalArgumentException("Not exist image $imageId")