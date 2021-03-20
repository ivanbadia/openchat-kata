package org.openchat.acceptance

import org.openchat.OpenChatApp
import ratpack.test.MainClassApplicationUnderTest

@Singleton(property = "app")
class OpenChat {
    @Delegate
    MainClassApplicationUnderTest appUnderTest = new MainClassApplicationUnderTest(OpenChatApp)
}
