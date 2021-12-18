package me.yogendra.iddv2;

import android.net.Uri
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test;

class UriParsing {
  @Test
  fun uriParts() {
    var  phoneNumber = "+91123456789"
    var uri = Uri.fromParts("tel", Uri.encode(phoneNumber),"");
    assertThat(uri.toString(), `is`("tel:%2B91123456789"))
//    var expected = "91123456789"



  }
}
