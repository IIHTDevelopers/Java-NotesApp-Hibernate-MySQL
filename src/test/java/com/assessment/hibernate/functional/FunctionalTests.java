package com.assessment.hibernate.functional;

import static com.assessment.hibernate.testutils.MasterData.getNote;
import static com.assessment.hibernate.testutils.TestUtils.businessTestFile;
import static com.assessment.hibernate.testutils.TestUtils.currentTest;
import static com.assessment.hibernate.testutils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Component;

import com.notesapplication.repository.NoteRepositoryImpl;

@Component
public class FunctionalTests {

	@InjectMocks
	private static NoteRepositoryImpl noteDao;

	@Mock
	static SessionFactory sessionFactory;

	@Mock
	static Session session;

	@Mock
	static Transaction transaction;

	@Mock
	static Criteria criteria;

	@BeforeAll
	public static void setupMock() {
		MockitoAnnotations.initMocks(new FunctionalTests());
		sessionFactory = mock(SessionFactory.class);
		session = mock(Session.class);
		criteria = mock(Criteria.class);
		noteDao.setSessionFactory(sessionFactory);
	}

	@Test
	@Transactional
	public void testAddNote() throws Exception {
		try {
			Integer id = 1;
			when(sessionFactory.openSession()).thenReturn(session);
			when(session.save(getNote())).thenReturn(id);
			when(session.getTransaction()).thenReturn(transaction);

			boolean status = noteDao.create(getNote());

			yakshaAssert(currentTest(), (status == true ? "true" : "false"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), "false", businessTestFile);
		}
	}
}
