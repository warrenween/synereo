package com.protegra_ati.agentservices.core.schema.persistence

import org.junit._
import Assert._
import org.specs2.mutable._
import org.junit.runner._
import org.specs2.runner._
import java.util.UUID
import java.util.Date
import java.util.Locale
import java.util.Properties
import java.io.Writer
import java.io.FileWriter
import java.io.IOException
import com.protegra_ati.agentservices.core.schema._
import com.protegra_ati.agentservices.core.schema.behaviors.Tracking
import com.protegra_ati.agentservices.core.schema.Constants._
import com.protegra_ati.agentservices.core.schema.util._
import scala.collection.JavaConversions._
import com.protegra_ati.agentservices.core.messages.invitation.CreateInvitationRequest
import org.joda.time.DateTime

class StorableDataTest extends SpecificationWithJUnit
{
  val id = UUID.fromString("99595a09-8f3b-48a9-ad6d-ccd5d2782e71").toString
  "store key" should {
    //    val NULL_DATA_TIME = "1980-01-01T00:00:00.000-00:00"
    //val created = new DateTime(NULL_DATA_TIME)
    // val connection = new Connection("business", "connectionType", "alias", null, null, "autoApprove", List(ConnectionPolicy.ReferralDisabled, ConnectionPolicy.SearchDisabled),created)


    "generate store key correctly for Data" in {
      val conn = new MockConnection
      conn.id = id
      val storeKey = conn.toStoreKey
      println("storeKey: " + storeKey)
      //      assertEquals("mockConnection(\"Jennifer\",\"true\",\"100\",\"01-02-1901 00:00:00\",\"1.2345\",\"\",\"0\",\"false\",\"\",\"0\",\"0\",\"0.0\",\"0.0\",\"\")", storeKey)
      //      storeKey must be_==("mockConnection(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"en\"),name(\"Jennifer\"),isBlue(\"true\"),age(\"100\"),birthDate(\"01-02-1901 00:00:00\"),amt(\"1.2345\"),test0(\"[a, b]\"),test0Empty(\"[]\"),test1(\"\"),test2(\"0\"),test3(\"false\"),test4(\"\"),test5(\"0\"),test6(\"0\"),test7(\"0.0\"),test8(\"0.0\"),test9(\"\"),test10(\"\")))")
      storeKey must be_==("mockConnection(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"en\"),name(\"Jennifer\"),isBlue(\"true\"),age(\"100\"),birthDate(\"01-02-1901 00 00 00\"),amt(\"1 2345\"),test0(\"[a, b]\"),test0Empty(\"[]\"),test1(\"\"),test2(\"0\"),test3(\"false\"),test4(\"\"),test5(\"0\"),test6(\"0\"),test7(\"0 0\"),test8(\"0 0\"),test9(\"\"),test10(\"\")))")
    }

    //Connection needs to be updated to output the right key again
    "generate store key correctly for Connection" in {
      skipped("TODO: need to update the test")

      val brokerId = "Broker" + UUID.fromString("bbbbbbbb-d417-4d71-ad94-8c766907381b")
      val connection = ConnectionFactory.createConnection("alias", ConnectionCategory.Business.toString, ConnectionCategory.Self.toString, "connectionType", brokerId, brokerId)
      connection.id = id

      val storeKey = connection.toStoreKey
      println("storeKey: " + storeKey)
      val expected = ( "connection(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"en\"),category(\"self\"),connectionType(\"connectionType\"),alias(\"alias\"),readCnxn(\"\"),writeCnxn(\"\"),autoApprove(\"true\"),policies(\"[]\"),created(\"" + connection.created + "\")))" )
      println("expected: " + expected)
      storeKey must be_==(expected)
    }

    "generate store key correctly for french Data" in {
      val conn = new MockConnection
      conn.id = id
      conn.setLocaleCode(Locale.FRENCH.toString())
      val storeKey = conn.toStoreKey
      println("storeKey: " + storeKey)
      //      assertEquals("mockConnection(\"Jennifer\",\"true\",\"100\",\"01-02-1901 00:00:00\",\"1.2345\",\"\",\"0\",\"false\",\"\",\"0\",\"0\",\"0.0\",\"0.0\",\"\")", storeKey)
      //      storeKey must be_==("mockConnection(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"fr\"),name(\"Jennifer\"),isBlue(\"true\"),age(\"100\"),birthDate(\"01-02-1901 00:00:00\"),amt(\"1.2345\"),test0(\"[a, b]\"),test0Empty(\"[]\"),test1(\"\"),test2(\"0\"),test3(\"false\"),test4(\"\"),test5(\"0\"),test6(\"0\"),test7(\"0.0\"),test8(\"0.0\"),test9(\"\"),test10(\"\")))")
      storeKey must be_==("mockConnection(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"fr\"),name(\"Jennifer\"),isBlue(\"true\"),age(\"100\"),birthDate(\"01-02-1901 00 00 00\"),amt(\"1 2345\"),test0(\"[a, b]\"),test0Empty(\"[]\"),test1(\"\"),test2(\"0\"),test3(\"false\"),test4(\"\"),test5(\"0\"),test6(\"0\"),test7(\"0 0\"),test8(\"0 0\"),test9(\"\"),test10(\"\")))")
    }

    "generate store key correctly for a System Data" in {
      val conn = new MockConnection
      conn.id = id
      val systemData = new SystemData[ MockConnection ](conn)
      val storeKey = systemData.toStoreKey
      println("storeKey: " + storeKey)
      //      assertEquals("mockConnection(\"Jennifer\",\"true\",\"100\",\"01-02-1901 00:00:00\",\"1.2345\",\"\",\"0\",\"false\",\"\",\"0\",\"0\",\"0.0\",\"0.0\",\"\")", storeKey)
      //      storeKey must be_==("systemData(mockConnection(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"en\"),name(\"Jennifer\"),isBlue(\"true\"),age(\"100\"),birthDate(\"01-02-1901 00:00:00\"),amt(\"1.2345\"),test0(\"[a, b]\"),test0Empty(\"[]\"),test1(\"\"),test2(\"0\"),test3(\"false\"),test4(\"\"),test5(\"0\"),test6(\"0\"),test7(\"0.0\"),test8(\"0.0\"),test9(\"\"),test10(\"\"))))")
      storeKey must be_==("systemData(mockConnection(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"en\"),name(\"Jennifer\"),isBlue(\"true\"),age(\"100\"),birthDate(\"01-02-1901 00 00 00\"),amt(\"1 2345\"),test0(\"[a, b]\"),test0Empty(\"[]\"),test1(\"\"),test2(\"0\"),test3(\"false\"),test4(\"\"),test5(\"0\"),test6(\"0\"),test7(\"0 0\"),test8(\"0 0\"),test9(\"\"),test10(\"\"))))")
    }

    "generate store key correctly for a Profile" in {
      val idN = "MY_ID"
      val mockProfile = new Profile("FirstName", "LastName", "desc?", "123456789@test.com", "CA", "someCAprovince", "city", "postalCode", "website")
      mockProfile.imageHashCode = "1234"
      mockProfile.id = idN
      mockProfile.localeCode = "en"
      val storeKey = mockProfile.toStoreKey
      //      val expectedStoreKey = "profile(fields(id(\"" + idN + "\"),localeCode(\"en\"),firstName(\"FirstName\"),lastName(\"LastName\"),description(\"\"),emailAddress(\"123456789@test.com\"),country(\"CA\"),region(\"someCAprovince\"),city(\"city\"),postalCode(\"postalCode\"),website(\"website\"),image(\"\")))"
      val expectedStoreKey = "profile(fields(id(\"" + idN + "\"),localeCode(\"en\"),firstName(\"FirstName\"),lastName(\"LastName\"),description(\"desc \"),emailAddress(\"123456789 test com\"),country(\"CA\"),region(\"someCAprovince\"),city(\"city\"),postalCode(\"postalCode\"),website(\"website\"),imageHashCode(\"1234\")))"
      //      println("created  storeKey4Profile: " + storeKey)
      //      println("expected storeKey4Profile: " + expectedStoreKey)
      storeKey must be_==(expectedStoreKey)
    }


    "generate store key correctly for Data with Tracking" in {
      val trackedConn = new MockConnectionTracked
      trackedConn.id = id

      val storeKey = trackedConn.toStoreKey
      println("storeKey: " + storeKey)
      //      assertEquals("mockConnection(\"Jennifer\",\"true\",\"100\",\"01-02-1901 00:00:00\",\"1.2345\",\"\",\"0\",\"false\",\"\",\"0\",\"0\",\"0.0\",\"0.0\",\"\")", storeKey)
      //      val expected = "mockConnectionTracked(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"en\"),name(\"Jennifer\"),isBlue(\"true\"),age(\"100\"),birthDate(\"01-02-1901 00:00:00\"),amt(\"1.2345\"),test0(\"[a, b]\"),test0Empty(\"[]\"),test1(\"\"),test2(\"0\"),test3(\"false\"),test4(\"\"),test5(\"0\"),test6(\"0\"),test7(\"0.0\"),test8(\"0.0\"),test9(\"\"),test10(\"\"),sent(\"\"),delivered(\"\"),viewed(\"\")))"
      val expected = "mockConnectionTracked(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"en\"),name(\"Jennifer\"),isBlue(\"true\"),age(\"100\"),birthDate(\"01-02-1901 00 00 00\"),amt(\"1 2345\"),test0(\"[a, b]\"),test0Empty(\"[]\"),test1(\"\"),test2(\"0\"),test3(\"false\"),test4(\"\"),test5(\"0\"),test6(\"0\"),test7(\"0 0\"),test8(\"0 0\"),test9(\"\"),test10(\"\"),sent(\"\"),delivered(\"\"),viewed(\"\")))"
      storeKey must be_==(expected)
    }

    "generate store key correctly for Data with a Message and a Hashmap" in {
      val fromDetails = new java.util.HashMap[ String, Data ]();
      val tempProfile = new Profile();
      tempProfile.setFirstName("first");
      tempProfile.setLastName("last");
      fromDetails.put(tempProfile.formattedClassName, tempProfile);

      val postToTarget = new Post("subject2Target", "body", fromDetails);
      val postToBroker = new Post("subject2Broker", "body", fromDetails);
      val msg = new CreateInvitationRequest(null, "", "", "", "", "", "", "", postToTarget, postToBroker);
      val persistedMessage = new PersistedMessage[ CreateInvitationRequest ](msg)
      persistedMessage.id = id

      val storeKey = persistedMessage.toStoreKey
      println("storeKey: " + storeKey)
      //      persistedMessage(fields(id("ec149c51-88f8-476c-bda7-8dd721d14a3f"),localeCode("en"),message(""),persisted("2012-05-02T22:01:02.903-05:00")))
      val persistedRaw = "" + persistedMessage.persisted
      val persisted = persistedRaw.replace(".", " ").replace(":"," ")
      val expected = "persistedMessage(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"en\"),message(\"\"),persisted(\"" + persisted + "\"),rejected(\"\"),ignored(\"\"),archived(\"\")))"
      println("EXPECTED: " + expected)
      storeKey must be_==(expected)
    }

    "generate store key correctly for an Image" in {
      val data: Image = new Image("name", "type", "content hasn't be visible in a toStoreKey", "myMetadata")
      val id = UUID.fromString("99595a09-8f3b-48a9-ad6d-ccd5d2782e71").toString
      data.id = id
      val given = data.toStoreKey
      val expected = "image(" + FIELDS + "(id(\"99595a09-8f3b-48a9-ad6d-ccd5d2782e71\"),localeCode(\"en\"),name(\"name\"),contentType(\"type\"),content(\"\"),metadata(\"myMetadata\")))"
      //      println("given:" + given)
      //      println("expected:" + expected)
      given must be_==(expected)
    }

    "generate delete key correctly for a Profile" in {
      val idN = "MY_ID"
      val mockProfile = new Profile("FirstName", "LastName", "desc?", "123456789@test.com", "CA", "someCAprovince", "city", "postalCode", "website")
      mockProfile.imageHashCode = "1234"
      mockProfile.id = idN
      mockProfile.localeCode = "en"
      val deleteKey = mockProfile.toDeleteKey
      //      val expectedStoreKey = "profile(fields(id(\"" + idN + "\"),localeCode(\"en\"),firstName(\"FirstName\"),lastName(\"LastName\"),description(\"\"),emailAddress(\"123456789@test.com\"),country(\"CA\"),region(\"someCAprovince\"),city(\"city\"),postalCode(\"postalCode\"),website(\"website\"),image(\"\")))"
      val expectedStoreKey = "profile(fields(id(\"" + idN + "\"),localeCode(_),firstName(_),lastName(_),description(_),emailAddress(_),country(_),region(_),city(_),postalCode(_),website(_),imageHashCode(_)))"
      //      println("created  storeKey4Profile: " + storeKey)
      //      println("expected storeKey4Profile: " + expectedStoreKey)
      deleteKey must be_==(expectedStoreKey)
    }
  }
}
