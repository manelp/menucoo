/*
 * Copyright (c) 2022 Manel Perez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.menucoo.domain.model

import java.nio.ByteBuffer
import java.util.Base64
import java.util.UUID

import scala.util.Try

object UUIDSyntax {

  extension (r: UUID) {
    private def uuidToBytes: Array[Byte] = {
      val bb = ByteBuffer.allocate(16)
      bb.putLong(r.getMostSignificantBits())
      bb.putLong(r.getLeastSignificantBits())
      bb.array()
    }
    def shortString: String = new String(Base64.getUrlEncoder().encode(uuidToBytes))
  }
  private def bytesToUUID(source: Array[Byte]) = {
    val bb           = ByteBuffer.wrap(source)
    val mostSigBits  = bb.getLong()
    val leastSigBits = bb.getLong()
    new UUID(mostSigBits, leastSigBits)
  }

  def fromShortString(source: String): Try[UUID] = Try(bytesToUUID(Base64.getUrlDecoder().decode(source.getBytes())))

}
